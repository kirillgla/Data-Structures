#include <malloc.h>
#include <stdio.h>

#include "avlTree.h"

AvlTree *newAvlTree() {
    AvlTree *result = (AvlTree *) malloc(sizeof(AvlTree));
    if (!result) {
        return NULL;
    }

    result->root = NULL;
    return result;
}

void deleteAvlTree(AvlTree *this) {
    if (!this) {
        return;
    }

    deleteAvlTreeNode(this->root);
    free(this);
}

int insertIntoAvlTree(AvlTree *this, avlContent_t value) {
    if (!this) {
        return 1;
    }

    if (this->root) {
        return insertIntoAvlTreeNode(this->root, value);
    }

    AvlTreeNode *newNode = newAvlTreeNode(value);
    if (!newNode) {
        return 2;
    }

    this->root = newNode;
    return 0;
}

int heightOfAvlTree(AvlTree *this) {
    if (!this) {
        return 0;
    }

    return heightOfAvlTreeNode(this->root);
}

int findInAvlTree(AvlTree *this, avlContent_t value) {
    if (!this) {
        return 0;
    }

    return findInAvlTreeNode(this->root, value);
}
