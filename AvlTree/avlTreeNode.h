#ifndef AVL_TREE_NODE_H
#define AVL_TREE_NODE_H

#ifndef AVL_CONTENT_T
#define AVL_CONTENT_T
// Avl Tree Content type should be comparable
typedef int avlContent_t;
#endif

typedef struct AvlTreeNode {
    int height;
    avlContent_t value;
    struct AvlTreeNode *left;
    struct AvlTreeNode *right;
} AvlTreeNode;

AvlTreeNode *newAvlTreeNode(avlContent_t value);

void deleteAvlTreeNode(AvlTreeNode *this);

/// @return 0 on success, error code otherwise
int insertIntoAvlTreeNode(AvlTreeNode *this, avlContent_t value);

int heightOfAvlTreeNode(AvlTreeNode *this);

void updateAvlTreeNode(AvlTreeNode *this, AvlTreeNode *insertationPlace);

void updateHeightOfAvlTreeNode(AvlTreeNode *this);

void restoreBalacneOfAvlTreeNode(AvlTreeNode *this, AvlTreeNode *insertationPlace);

/// @return whether given subtree contains value or not
int findInAvlTreeNode(AvlTreeNode *this, avlContent_t value);

#endif // AVL_TREE_NODE_H