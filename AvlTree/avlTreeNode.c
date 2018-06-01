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
    result->height = 1;
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

int insertIntoAvlTreeNode(AvlTreeNode *this, avlContent_t value, avlComparator_t comparator) {
    if (!this || !comparator) {
        return 1;
    }

    int comparisonResult = comparator(value, this->value);
    if (!comparisonResult) {
        return 3;
    }

    AvlTreeNode **insertionPlace = comparator(value, this->value) < 0 ? &(this->left) : &(this->right);
    if (*insertionPlace) {
        int result = insertIntoAvlTreeNode(*insertionPlace, value, comparator);
        if (result) {
            return result;
        }
        restoreBalanceOfAvlTreeNode(this);
        return 0;
    }

    AvlTreeNode *newNode = newAvlTreeNode(value);
    if (!newNode) {
        return 2;
    }

    *insertionPlace = newNode;
    restoreBalanceOfAvlTreeNode(this);
    return 0;
}

int heightOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return 0;
    }

    return this->height;
}

int deltaOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return 0;
    }

    return heightOfAvlTreeNode(this->right) - heightOfAvlTreeNode(this->left);
}

void updateHeightOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return;
    }

    int leftHeight = heightOfAvlTreeNode(this->left);
    int rightHeight = heightOfAvlTreeNode(this->right);

    if (leftHeight > rightHeight) {
        this->height = leftHeight + 1;
    }
    else {
        this->height = rightHeight + 1;
    }
}

void restoreBalanceOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return;
    }

    int delta = deltaOfAvlTreeNode(this);

    if (delta > -2 && delta < 2) {
        updateHeightOfAvlTreeNode(this);
        return;
    }

    // The child that was modified
    AvlTreeNode *child = delta < 0 ? this->left : this->right;
    int childDelta = deltaOfAvlTreeNode(child);

    if (delta > 0) {
        if (childDelta > 0) {
            rotateLeftAvlTreeNode(this);
        }
        else {
            // childDelta < 0
            rotateRightAvlTreeNode(child);
            rotateLeftAvlTreeNode(this);
        }
    }
    else {
        // delta < 0
        if (childDelta < 0) {
            rotateRightAvlTreeNode(this);
        }
        else {
            rotateLeftAvlTreeNode(child);
            rotateRightAvlTreeNode(this);
        }
    }
}

void rotateLeftAvlTreeNode(AvlTreeNode *this) {
    if (!this || !this->right) {
        return;
    }

    AvlTreeNode *rootAddress = this;
    AvlTreeNode *childAddress = this->right;
    AvlTreeNode formerRoot = *rootAddress;
    AvlTreeNode formerChild = *childAddress;

    *rootAddress = formerChild;
    *childAddress = formerRoot;
    childAddress->right = rootAddress->left;
    rootAddress->left = childAddress;

    updateHeightOfAvlTreeNode(childAddress);
    updateHeightOfAvlTreeNode(rootAddress);
}

void rotateRightAvlTreeNode(AvlTreeNode *this) {
    if (!this || !this-> left) {
        return;
    }

    AvlTreeNode *rootAddress = this;
    AvlTreeNode *childAddress = this->left;
    AvlTreeNode formerRoot = *rootAddress;
    AvlTreeNode formerChild = *childAddress;

    *rootAddress = formerChild;
    *childAddress = formerRoot;
    childAddress->left = rootAddress->right;
    rootAddress->right = childAddress;

    updateHeightOfAvlTreeNode(childAddress);
    updateHeightOfAvlTreeNode(rootAddress);
}

int findInAvlTreeNode(AvlTreeNode *this, avlContent_t value, avlComparator_t comparator) {
    if (!this) {
        return 0;
    }

    if (!comparator(value, this->value)) {
        return 1;
    }

    if (comparator(value, this->value) < 0) {
        return findInAvlTreeNode(this->left, value, comparator);
    }
    else {
        return findInAvlTreeNode(this->right, value, comparator);
    }
}
