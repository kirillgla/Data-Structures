#include <malloc.h>
#include <stdio.h>

#include "avlTree.h"

AvlTree *newAvlTree(avlComparator_t comparator) {
    if (!comparator) {
        return NULL;
    }

    AvlTree *result = (AvlTree *) malloc(sizeof(AvlTree));
    if (!result) {
        return NULL;
    }

    result->root = NULL;
    result->comparator = comparator;
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
        return insertIntoAvlTreeNode(this->root, value, this->comparator);
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

    return findInAvlTreeNode(this->root, value, this->comparator);
}

int setComparator(AvlTree *this, avlComparator_t comparator) {
    if (!this || !comparator) {
        return 1;
    }

    this->comparator = comparator;
    return 0;
}
