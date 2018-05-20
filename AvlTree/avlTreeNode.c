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

int insertIntoAvlTreeNode(AvlTreeNode *this, avlContent_t value) {
    if (!this) {
        return 1;
    }

    AvlTreeNode **insertationPlace = value < this->value ? &(this->left) : &(this->right);
    if (*insertationPlace) {
        int result = insertIntoAvlTreeNode(*insertationPlace, value);
        if (result) {
            return result;
        }
        updateAvlTreeNode(this);
        return 0;
    }

    AvlTreeNode *newNode = newAvlTreeNode(value);
    if (!newNode) {
        return 2;
    }

    *insertationPlace = newNode;
    updateAvlTreeNode(this);
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

void updateAvlTreeNode(AvlTreeNode *this) {
    restoreBalacneOfAvlTreeNode(this);
    // TODO: this recalculation might be redundant
    updateHeightOfAvlTreeNode(this);
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

void restoreBalacneOfAvlTreeNode(AvlTreeNode *this) {
    if (!this) {
        return;
    }

    int delta = deltaOfAvlTreeNode(this);
    
    if (delta > -2 && delta < 2) {
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
