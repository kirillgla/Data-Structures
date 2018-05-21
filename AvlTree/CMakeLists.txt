cmake_minimum_required(VERSION 3.10)
project(AvlTree C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(AvlTree
        avlTree.c
        avlTree.h
        avlTreeNode.c
        avlTreeNode.h
        main.c
        )
