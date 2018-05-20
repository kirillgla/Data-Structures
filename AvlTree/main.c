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

    for (int i = 12; i < 17; i++) {
        result = insertIntoAvlTree(tree, i);
        if (result) {
            printf("Error %d\n", result);
            deleteAvlTree(tree);
            return result;
        }
    }

    result = findInAvlTree(tree, 30);
    printf("30 %sfound\r\n", result ? "" : "not ");
    printf("AVL tree has heigth of %d\r\n", heightOfAvlTree(tree));

    deleteAvlTree(tree);
}
