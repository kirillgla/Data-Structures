cmake_minimum_required(VERSION 3.10)
project(LinkedList C)

set(CMAKE_C_STANDARD 99)

add_executable(LinkedList main.c linkedListNode.c linkedListNode.h linkedList.c linkedList.h)
