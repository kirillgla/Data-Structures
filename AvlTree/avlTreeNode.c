#include <malloc.h>
#include <stdio.h>

#include "avlTreeNode.h"

AvlTreeNode *newAvlTreeNode(avlContent_t value) {
    AvlTreeNode *result = malloc(sizeof(AvlTreeNode));
    if (!result) {
        return NULL;
    }

    result->left = NULL;
    result->right = NULL;
    result->heigth = 1;
    result->value = value;
    return result;
}

void deleteAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return;
    }

    deleteAvlTreeNode(this->left);
    deleteAvlTreeNode(this->right);
    free(this);
}

int insertIntoAvlTreeNode(AvlTreeNode *this,  avlContent_t value) {
    if (!this) {
        return 1;
    }

    if (value < this->value) {
        if (this->left) {
            return insertIntoAvlTreeNode(this->left, value);
        }

        AvlTreeNode *newNode = newAvlTreeNode(value);
        if (!newNode) {
            return 2;
        }

        this->left = newNode;
    }
    else {
        if (this->right) {
            return insertIntoAvlTreeNode(this->right, value);
        }

        AvlTreeNode *newNode = newAvlTreeNode(value);
        if (!newNode) {
            return 2;
        }

        this->right = newNode;
    }

    // TODO: restore balance
    // TODO: recalculate heigths
    return 0;
}

int heigthOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return 0;
    }

    return this->heigth;
}

int findInAvlTreeNode(AvlTreeNode *this, avlContent_t value) {
    if (!this) {
        return 0;
    }

    if (value == this->value) {
        return 1;
    }

    if (value < this->value) {
        return findInAvlTreeNode(this->left, value);
    }
    else {
        return findInAvlTreeNode(this->right, value);
    }
}
