cmake_minimum_required(VERSION 3.10)
project(DynamicArray C)

set(CMAKE_C_STANDARD 11)

add_executable(DynamicArray main.c dynamicArray.c dynamicArray.h)
