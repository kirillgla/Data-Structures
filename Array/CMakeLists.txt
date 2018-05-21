cmake_minimum_required(VERSION 3.10)
project(Array C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(Array
        array.c
        array.h
        main.c)
