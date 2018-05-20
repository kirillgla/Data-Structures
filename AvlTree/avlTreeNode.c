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
            int result = insertIntoAvlTreeNode(this->left, value);
            if (result) {
                return result;
            }
            updateHeigthOfAvlTreeNode(this);
            return 0;
        }

        AvlTreeNode *newNode = newAvlTreeNode(value);
        if (!newNode) {
            return 2;
        }

        this->left = newNode;
    }
    else {
        if (this->right) {
            // TODO: remove duplicated code
            int result = insertIntoAvlTreeNode(this->right, value);
            if (result) {
                return result;
            }
            updateHeigthOfAvlTreeNode(this);
            return 0;
        }

        AvlTreeNode *newNode = newAvlTreeNode(value);
        if (!newNode) {
            return 2;
        }

        this->right = newNode;
    }

    // TODO: restore balance
    return 0;
}

int heigthOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return 0;
    }

    return this->heigth;
}

void updateHeigthOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return;
    }

    int leftHeigth = heigthOfAvlTreeNode(this->left);
    int rightHeigth = heigthOfAvlTreeNode(this->right);

    if (leftHeigth > rightHeigth) {
        this->heigth = leftHeigth + 1;
    }
    else {
        this->heigth = rightHeigth + 1;
    }
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
