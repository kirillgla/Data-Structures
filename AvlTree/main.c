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

int compare(avlContent_t first, avlContent_t second) {
    return first - second;
}

static const unsigned int SEED = 1234567;
static const unsigned int DATA_SIZE = 1000;

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

void printFirst(FILE *file, int *data, int count, int ellipsis) {
    fprintf(file, "[");
    for (int i = 0; i < count - 1; i++) {
        fprintf(file,  "%d, ", data[i]);
    }
    fprintf(file, "%d", data[count - 1]);
    if (ellipsis) {
        fprintf(file, ", ...");
    }
    fprintf(file, "]\r\n");
}

int main(int argc, char **argv) {
    AvlTree *tree;
    int *data;
    int result;
    int duplicates = 0;

    tree = newAvlTree(&compare);
    data = createData();
    if (!tree || !data) {
        fprintf(stderr, "Error: could not allocate memory for structures");
        freeAll(tree, data);
        return 2;
    }

    for (int i = 0; i < 1000; i++) {
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

    if (duplicates) {
        printf("Warning: there were %d out of %d duplicate values in input\r\n", duplicates, DATA_SIZE);
    }

    for (int i = 0; i < 1000; i++) {
        result = findInAvlTree(tree, data[i]);
        if (!result) {
            printf("Error: data[%d] (= %d) not found\r\n", i, data[i]);
            freeAll(tree, data);
            return result;
        }
    }

    printf("AVL tree has height of %d\r\n", heightOfAvlTree(tree));
    printf("All ok\r\n");
    freeAll(tree, data);
}

// TODO: implement deleting
