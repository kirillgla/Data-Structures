#ifndef AVL_TREE_H
#define AVL_TREE_H

#include "avlTreeNode.h"

typedef struct {
    AvlTreeNode *root;
    avlComparator_t comparator;
} AvlTree;

AvlTree *newAvlTree(avlComparator_t comparator);

void deleteAvlTree(AvlTree *this);

/// @return error code
int insertIntoAvlTree(AvlTree *this, avlContent_t value);

int heightOfAvlTree(AvlTree *this);

/// @return boolean result
int findInAvlTree(AvlTree *this, avlContent_t value);

/// @return error code
int removeFromAvlTree(AvlTree *this, avlContent_t value);

#endif // AVL_TREE_H
