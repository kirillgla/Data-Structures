#ifdef AVL_CONTENT_T
#error avlContent_t should not be defined before main
#endif

#define AVL_CONTENT_T
typedef double avlContent_t;


#include <stdio.h>

#include "avlTree.h"

int main(int argc, char **argv) {
    AvlTree *tree;
    int result;

    tree = newAvlTree();

    for (int i = 12; i < 33; i++) {
        result = insertIntoAvlTree(tree, i);
        if (result) {
            printf("Error %d", result);
            deleteAvlTree(tree);
            return result;
        }
    }

    result = findInAvlTree(tree, 30);
    if (result) {
        printf("ok");
    }

    deleteAvlTree(tree);
}
