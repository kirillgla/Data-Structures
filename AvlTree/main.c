#ifdef AVL_CONTENT_T
#error avlContent_t should not be defined before main
#endif

#define AVL_CONTENT_T
// TODO: why 'typedef char ...' results in height of 11?
typedef double avlContent_t;

#include <stdio.h>
#include <mem.h>

#include "avlTree.h"

int compare(avlContent_t first, avlContent_t second) {
    double difference = first - second;
    return difference > 0 ? 1 : difference < 0 ? -1 : 0;
}

int main(int argc, char **argv) {
    AvlTree *tree;
    int result;

    tree = newAvlTree(&compare);

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
            printf("Error: %d not found\r\n", i);
            deleteAvlTree(tree);
            return result;
        }
    }

    printf("AVL tree has height of %d\r\n", heightOfAvlTree(tree));
    printf("All ok");
    deleteAvlTree(tree);
}

