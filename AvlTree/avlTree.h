#ifndef AVL_TREE_H
#define AVL_TREE_H

#include "avlTreeNode.h"

typedef struct {
    AvlTreeNode *root;
    avlComparator_t comparator;
} AvlTree;

AvlTree *newAvlTree(avlComparator_t comparator);

void deleteAvlTree(AvlTree *this);

/// @return 0 on success, error code otherwise
int insertIntoAvlTree(AvlTree *this, avlContent_t value);

int heightOfAvlTree(AvlTree *this);

/// @return whether this contains value or not
int findInAvlTree(AvlTree *this, avlContent_t value);

#endif // AVL_TREE_H
