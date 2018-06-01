#ifndef AVL_TREE_NODE_H
#define AVL_TREE_NODE_H

#ifndef AVL_CONTENT_T
#define AVL_CONTENT_T

typedef int avlContent_t;

#endif

/// comparator is supposed to return:
///     0 if arguments are equal
///     positive integer if first is greater than the second
///     negative integer if first is smaller than the seconds
typedef int (*avlComparator_t)(avlContent_t, avlContent_t);

typedef struct AvlTreeNode {
    int height;
    avlContent_t value;
    struct AvlTreeNode *left;
    struct AvlTreeNode *right;
} AvlTreeNode;

AvlTreeNode *newAvlTreeNode(avlContent_t value);

void deleteAvlTreeNode(AvlTreeNode *this);

/// @return 0 on success, error code otherwise
int insertIntoAvlTreeNode(AvlTreeNode *this, avlContent_t value, avlComparator_t comparator);

int heightOfAvlTreeNode(AvlTreeNode *this);

int deltaOfAvlTreeNode(AvlTreeNode *this);

void updateHeightOfAvlTreeNode(AvlTreeNode *this);

void restoreBalanceOfAvlTreeNode(AvlTreeNode *this);

void rotateLeftAvlTreeNode(AvlTreeNode *this);

void rotateRightAvlTreeNode(AvlTreeNode *this);

/// @return whether given subtree contains value or not
int findInAvlTreeNode(AvlTreeNode *this, avlContent_t value, avlComparator_t comparator);

#endif // AVL_TREE_NODE_H
