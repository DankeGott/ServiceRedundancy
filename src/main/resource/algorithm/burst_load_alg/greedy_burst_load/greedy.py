# -*- coding:utf-8 -*-
import numpy as np
from typing import Tuple, List
from progressbar import progressbar
import seaborn as sns
import matplotlib.pyplot as plt
from settings import *
import json


class EdgeServer:
    def __init__(self, freq: float, ram: float, storage: float):
        self.freq = freq
        self.ram = ram
        self.storage = storage

    def __str__(self):
        return f'EdgeServer: freq={self.freq},ram={self.ram},storage={self.storage}'


class EdgeLink:
    def __init__(self, travel_tm: float, link_capacity: float):
        self.travel_tm = travel_tm
        self.capacity = link_capacity

    def __str__(self):
        return f'EdgeLink: travel_tm={self.travel_tm}, link_capacity={self.capacity}'


class Task:
    def __init__(self, cycle: float, ram: float, storage: float):
        self.cycle = cycle
        self.ram = ram
        self.storage = storage

    def __str__(self):
        return f'Task: cycle={self.cycle}, ram={self.ram}, storage={self.storage}'


class NetGraph:
    def __init__(self, info_file: str):
        self.edge_servers: List[EdgeServer]
        self.edge_links: List[EdgeLink]
        self.graph_matrix: np.ndarray
        self.edge_servers, self.edge_links, self.graph_matrix, self.request_task_num = self._load_graph_info(
            info_file)

    @staticmethod
    def _load_graph_info(info_file) -> Tuple[List[EdgeServer], List[EdgeLink], np.ndarray]:
        edge_servers = []
        edge_links = []
        with open(info_file, 'r') as fr:
            content = fr.read()
        content = list(content.split('\n'))
        content = [line.strip() for line in content]
        i = 0
        while 'edge servers' not in content[i]:
            i += 1
        # read edge servers info
        i += 1
        while not content[i].startswith('#'):
            freq, ram, storage = content[i].split()
            edge_servers.append(EdgeServer(
                float(freq), float(ram), float(storage)))
            i += 1
        # read edge links info
        i += 1
        while not content[i].startswith('#'):
            travel_tm, link_capacity = content[i].split()
            edge_links.append(EdgeLink(float(travel_tm), int(link_capacity)))
            i += 1
        # read graph link info
        graph_matrix = np.ones([len(edge_servers), len(edge_servers)]) * -1
        i += 1
        while not content[i].startswith("#"):
            e1, e2, link = content[i].split()
            e1, e2, link = int(e1), int(e2), int(link)
            graph_matrix[e1, e2] = link
            graph_matrix[e2, e1] = link
            i += 1
        i += 1
        while i < len(content) and content[i]:
            request_num = int(content[i].strip())
            i += 1
        return edge_servers, edge_links, graph_matrix, request_num


class Greedy:
    """
    put the most time consuming tasks nearby
    """

    def __init__(self, net_graph: NetGraph, src_node_ind: int, dest_nodes_inds: List[int], num_request=NUM_REQUEST, block_size=BLOCK_SIZE):
        self.net_graph: NetGraph = net_graph
        self.src_node_ind: int = src_node_ind
        self.dest_nodes_inds: List[int] = dest_nodes_inds
        self.num_request: int = num_request
        self.block_size: int = block_size

        self.requests_tasks: List[Task] = self.init_requests()
        self.dests_path: List[List[int]] = self.shortest_path()

    def init_requests(self) -> List[Task]:
        cycle_reqs = np.random.randint(
            10000, 50000, size=self.num_request, dtype=int)
        ram_reqs = np.random.randint(
            1, 100, size=self.num_request, dtype=int)
        storage_reqs = np.random.randint(
            1, 100, size=self.num_request, dtype=int)
        task_seqs: List[Task] = []
        for i in range(self.net_graph.request_task_num):
            task_seqs.append(Task(cycle_reqs[i], ram_reqs[i], storage_reqs[i]))
        return task_seqs

    def greedy_arrange(self):
        """
        no iteration, just arrange the most time consuming tasks nearby
        """
        self.requests_tasks = list(
            reversed(sorted(self.requests_tasks, key=lambda x: x.cycle)))
        all_task_blocks: List[List[Task]] = []
        tmp_block: List[Task] = []
        i = 0
        while i < len(self.requests_tasks):
            while i < len(self.requests_tasks) and len(tmp_block) < self.block_size:
                tmp_block.append(self.requests_tasks[i])
                i += 1
            all_task_blocks.append(tmp_block.copy())
            tmp_block.clear()
        path_lens = [len(x) for x in self.dests_path]
        dest_inds = np.argsort(path_lens)
        block_per_dest = len(all_task_blocks) // len(self.dest_nodes_inds)
        solution: List[List[List[Task]]] = []
        for _ in range(len(self.dest_nodes_inds)):
            solution.append([])
        block_id = 0
        for d_id in dest_inds[:-1]:
            i = 0
            while i < block_per_dest:
                solution[d_id].append(all_task_blocks[block_id])
                block_id += 1
                i += 1
        while block_id < len(all_task_blocks):
            solution[dest_inds[-1]].append(all_task_blocks[block_id])
            block_id += 1
        tmp_solution: List[List[List[int]]] = []
        for i in range(len(solution)):
            tmp_solution.append([])
            for j in range(len(solution[i])):
                tmp_solution[i].append([])
                for k in range(len(solution[i][j])):
                    tmp_solution[i][j].append(
                        self.requests_tasks.index(solution[i][j][k]))
        return self.get_requests_tm_queue_v(tmp_solution)
        # return max([self.get_dest_requests_tm(solution[d_id], d_id) for d_id in range(len(self.dest_nodes_inds))])

    def _check_range_valid(self, lst: list, low, high, t=0):
        for i in range(int(low), int(high)):
            if lst[i] < t:
                return False
        return True

    def get_requests_tm_queue_v(self, solution: List[List[List[Task]]]):
        """
        calculating the running time of the tasks with certain policy
        besides, the time of each task at each stage will be recorded
        @return: the time stamp when all the tasks finished
        """
        servers = self.net_graph.edge_servers
        links = self.net_graph.edge_links
        server_ram_avail_time_list = [[e.ram]*1000 for e in servers]
        link_cap_avail_time_list = [[e.capacity]*1000 for e in links]

        tasks_wait_src_time_list = [0]*len(self.requests_tasks)
        tasks_migrate_time_list = [0]*len(self.requests_tasks)
        tasks_wait_exec_time_list = [0]*len(self.requests_tasks)
        tasks_exec_time_list = [0]*len(self.requests_tasks)

        edge_num_tasks_time_list = [[0]*1000 for _ in range(len(servers))]
        link_cap_status_time_list = [[0]*1000 for _ in range(len(links))]

        # each item is a tuple (task_idx, tm, from, to)
        tasks_migrate_info_list: List[tuple] = []
        # calculating the total time of running all the tasks
        longest_tm = 0
        for i in range(len(self.dest_nodes_inds)):
            for tasks_block in solution[i]:
                curr_tm = 0
                path = self.dests_path[i]
                # the link connect the src server
                link_idx = int(self.net_graph.graph_matrix[path[0], path[1]])
                while self._check_range_valid(link_cap_avail_time_list[link_idx], curr_tm,
                                              curr_tm + links[link_idx].travel_tm) is False:
                    edge_num_tasks_time_list[path[0]][curr_tm] += 1
                    curr_tm += 1
                for task_idx in tasks_block:
                    tasks_wait_src_time_list[task_idx] = curr_tm
                for j in range(int(curr_tm),  int(curr_tm+links[link_idx].travel_tm)):
                    link_cap_avail_time_list[link_idx][j] -= 1
                    link_cap_status_time_list[link_idx][j] += 1
                curr_tm += links[link_idx].travel_tm
                for j in range(1, len(path)-1):
                    link_idx = int(
                        self.net_graph.graph_matrix[path[j], path[j+1]])
                    while self._check_range_valid(link_cap_avail_time_list[int(link_idx)], int(curr_tm),
                                                  int(curr_tm+links[int(link_idx)].travel_tm)) is False:
                        edge_num_tasks_time_list[path[j]][int(curr_tm)] += 1
                        curr_tm += 1
                    for idx in tasks_block:
                        tasks_migrate_info_list.append(
                            (idx, curr_tm, path[j], path[j+1]))
                    for k in range(int(curr_tm), int(curr_tm+links[int(link_idx)].travel_tm)):
                        link_cap_avail_time_list[link_idx][k] -= 1
                        link_cap_status_time_list[link_idx][k] += 1
                    curr_tm += links[link_idx].travel_tm
                for task_idx in tasks_block:
                    tasks_migrate_time_list[task_idx] += curr_tm - \
                        tasks_wait_src_time_list[task_idx]
                # reach dest server
                exec_tm = max(
                    [self.requests_tasks[e].cycle//servers[i].freq for e in tasks_block])
                while self._check_range_valid(server_ram_avail_time_list[path[-1]], curr_tm, curr_tm+exec_tm) is False:
                    edge_num_tasks_time_list[path[-1]][int(curr_tm)] += 1
                    curr_tm += 1
                for task_idx in tasks_block:
                    tasks_wait_exec_time_list[task_idx] = curr_tm - tasks_wait_src_time_list[task_idx] -\
                        tasks_migrate_time_list[task_idx]
                    tasks_exec_time_list[task_idx] = self.requests_tasks[task_idx].cycle / \
                        servers[i].freq
                    for t in range(int(curr_tm), int(curr_tm+self.requests_tasks[task_idx].cycle // servers[i].freq)):
                        server_ram_avail_time_list[path[-1]
                                                   ][t] -= self.requests_tasks[task_idx].ram
                        edge_num_tasks_time_list[path[-1]][t] += 1
                curr_tm += exec_tm
                longest_tm = max(longest_tm, curr_tm)
        for i in range(len(edge_num_tasks_time_list)):
            edge_num_tasks_time_list[i] = edge_num_tasks_time_list[i][:int(
                longest_tm)]
        for i in range(len(link_cap_status_time_list)):
            link_cap_status_time_list[i] = link_cap_status_time_list[i][:int(
                longest_tm)]
        # print(f"task wait src = {tasks_wait_src_time_list}")
        # print(f"task migrate = {tasks_migrate_time_list}")
        # print(f"task wait for exec = {tasks_wait_exec_time_list}")
        # print(f"task exec = {tasks_exec_time_list}")
        # print(f"task migrate info = {tasks_migrate_info_list}")
        # print(f"edge num tasks time = {edge_num_tasks_time_list}")
        # print(f"link cap status time list = {link_cap_status_time_list}")
        # save the datas into files
        with open("tasks_tm_series.txt", "w", encoding="UTF-8") as f:
            for t1, t2, t3, t4 in list(zip(tasks_wait_src_time_list, tasks_migrate_time_list,
                                           tasks_wait_exec_time_list, tasks_exec_time_list)):
                print(f"{t1} {t2} {t3} {t4}", file=f)

        with open("tasks_migrate_info.txt", "w", encoding="UTF-8") as f:
            for task_idx, tm, src, dest in tasks_migrate_info_list:
                print(f"{task_idx} {tm} {src} {dest}", file=f)
        with open("edge_num_tasks.json", "w", encoding="UTF-8") as f:
            print(json.dumps(edge_num_tasks_time_list), file=f)
        with open("link_cap_status.json", "w", encoding="UTF-8") as f:
            print(json.dumps(link_cap_status_time_list), file=f)
        return longest_tm

    def get_dest_requests_tm(self, block_seq: List[List[Task]], dest_ind: int) -> float:
        migrate_time = len(self.dests_path[dest_ind])-1
        exec_tm = 0
        for block in block_seq:
            for task in block:
                exec_tm = max(
                    exec_tm, task.cycle / self.net_graph.edge_servers[dest_ind].freq)
        return migrate_time+exec_tm

    def shortest_path(self) -> List[List[int]]:
        """
        get the path from src to dests
        """
        visited = [False] * len(self.net_graph.edge_servers)
        dis = [float('inf')] * len(self.net_graph.edge_servers)
        pre = [i for i in range(len(self.net_graph.edge_servers))]
        dis[self.src_node_ind] = 0
        for i in range(len(dis)):
            u, min_dis = -1, float('inf')
            for j in range(len(dis)):
                if not visited[j] and dis[j] < min_dis:
                    min_dis = dis[j]
                    u = j
            if u == -1:
                break
            visited[u] = True
            for j in range(len(dis)):
                link = int(self.net_graph.graph_matrix[u, j])
                if not visited[j] and link != -1 and dis[u] + self.net_graph.edge_links[link].travel_tm < dis[j]:
                    dis[j] = dis[u] + self.net_graph.edge_links[link].travel_tm
                    pre[j] = u
        shortest_paths: List[List[int]] = []
        for i in range(len(self.dest_nodes_inds)):
            path = [self.dest_nodes_inds[i]]
            edge = pre[self.dest_nodes_inds[i]]
            while edge != self.src_node_ind:
                path.append(edge)
                edge = pre[edge]
            path.append(self.src_node_ind)
            path.reverse()
            shortest_paths.append(path)
        return shortest_paths


if __name__ == "__main__":
    net = NetGraph("info.txt")
    greedy = Greedy(net, 0, list(range(1, 10)))
    tm = greedy.greedy_arrange()
    with open("iter_info.txt", "w", encoding="UTF-8") as f:
        for i in range(10):
            print(tm, file=f)
