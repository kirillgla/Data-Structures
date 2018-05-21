#ifndef DYNAMIC_ARRAY_DYNAMIC_ARRAY_H
#define DYNAMIC_ARRAY_DYNAMIC_ARRAY_H

#ifndef DYNAMIC_ARRAY_TYPE
#define DYNAMIC_ARRAY_TYPE

typedef int dynamicContent_t;

#endif // DYNAMIC_ARRAY_TYPE

typedef struct {
    dynamicContent_t *data;
    int maxSize;
    int actualSize;
} DynamicArray;

DynamicArray *newDynamicArray(int maxSize);

void deleteDynamicArray(DynamicArray *this);

int setElementAt(DynamicArray *this, int index, dynamicContent_t value);

int getElementAt(DynamicArray *this, int index, dynamicContent_t *result);

int addToEndOfDynamicArray(DynamicArray *this, dynamicContent_t value);

int getNumberOfElements(DynamicArray *this);

#endif //DYNAMIC_ARRAY_DYNAMIC_ARRAY_H
