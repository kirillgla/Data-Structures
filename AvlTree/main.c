#ifdef AVL_CONTENT_T
#error avlContent_t should not be defined before main
#endif

#define AVL_CONTENT_T
typedef int avlContent_t;

#include <stdio.h>

#include "avlTree.h"

int main(int argc, char **argv) {
    AvlTree *tree;
    int result;

    tree = newAvlTree();

    for (int i = 0; i < 1000; i++) {
        result = insertIntoAvlTree(tree, i);
        if (result) {
            printf("Error %d\r\n", result);
            deleteAvlTree(tree);
            return result;
        }
    }

    for (int i = 0; i < 1000; i++) {
        result = findInAvlTree(tree, i);
        if (!result) {
            printf("Error: %d not fonund\r\n", i);
            deleteAvlTree(tree);
            return result;
        }
    }

    printf("AVL tree has heigth of %d\r\n", heightOfAvlTree(tree));
    deleteAvlTree(tree);
}
