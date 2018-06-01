#ifdef AVL_CONTENT_T
#error avlContent_t should not be defined before main
#endif

#define AVL_CONTENT_T
typedef int avlContent_t;

#include <stdio.h>
#include <mem.h>
#include <math.h>
#include <malloc.h>
#include <time.h>
#include <stdlib.h>

#include "avlTree.h"
#include "avlTreeNode.h"

int compare(avlContent_t first, avlContent_t second) {
    return first - second;
}

static const unsigned int SEED = 1234567;
static const unsigned int DATA_SIZE = 10000;

int *createData() {
    int *result = malloc(sizeof(int) * DATA_SIZE);
    if (!result) {
        return NULL;
    }

    srand(SEED);
    for (int i = 0; i < DATA_SIZE; i++) {
        result[i] = rand(); // NOLINT
    }

    return result;
}

void freeAll(AvlTree *tree, int *data) {
    deleteAvlTree(tree);
    free(data);
}

int main(int argc, char **argv) {
    AvlTree *tree;
    int *data;
    int result;
    int duplicates = 0;

    // Test creation
    tree = newAvlTree(&compare);
    data = createData();
    if (!tree || !data) {
        fprintf(stderr, "Error: could not allocate memory for structures");
        freeAll(tree, data);
        return 2;
    }

    // Test insertion
    for (int i = 0; i < DATA_SIZE; i++) {
        result = insertIntoAvlTree(tree, data[i]);
        if (result == 3) {
            duplicates++;
            continue;
        }
        if (result) {
            fprintf(stderr, "Error: could not insert value int tree");
            freeAll(tree, data);
            return 2;
        }
    }

    printf("With input size of %d, AVL tree height is %d\r\n", DATA_SIZE, heightOfAvlTree(tree));
    if (duplicates) {
        printf("Warning: there were %d out of %d duplicate values in input\r\n", duplicates, DATA_SIZE);
    }

    // Test search
    for (int i = 0; i < DATA_SIZE; i++) {
        result = findInAvlTree(tree, data[i]);
        if (!result) {
            printf("Error: data[%d] (= %d) not found\r\n", i, data[i]);
            freeAll(tree, data);
            return result;
        }
    }

    // Test removing
    result = removeFromAvlTree(tree, data[0]);
    if (result) {
        printf("Error: could not delete value\r\n");
        freeAll(tree, data);
        return result;
    }

    result = findInAvlTree(tree, data[0]);
    if (result) {
        printf("Error: removed value is still present in tree\r\n");
        freeAll(tree, data);
        return result;
    }

    printf("All ok\r\n");
    freeAll(tree, data);
}

// TODO: implement deleting
