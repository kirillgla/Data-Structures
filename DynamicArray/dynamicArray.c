#include <malloc.h>
#include "dynamicArray.h"

DynamicArray *newDynamicArray(int maxSize) {
    if (maxSize < 0) {
        return NULL;
    }
    if (maxSize == 0) {
        maxSize = 16;
    }

    DynamicArray *result = (DynamicArray *) malloc(sizeof(DynamicArray));
    if (!result) {
        return NULL;
    }

    dynamicContent_t *data = (dynamicContent_t *) malloc(sizeof(dynamicContent_t) * maxSize);
    if (!data) {
        free(result);
        return NULL;
    }

    result->data = data;
    result->actualSize = 0;
    result->maxSize = maxSize;
    return result;
}

void deleteDynamicArray(DynamicArray *this) {
    if (!this) {
        return;
    }
    free(this->data);
    free(this);
}

int setElementAt(DynamicArray *this, int index, dynamicContent_t value) {
    if (!this) {
        return 1;
    }
    if (index < 0 || index >= this->actualSize) {
        return 2;
    }
    this->data[index] = value;
    return 0;
}

int getElementAt(DynamicArray *this, int index, dynamicContent_t *result) {
    if (!this) {
        return 1;
    }
    if (index < 0 || index >=  this->actualSize) {
        return 2;
    }
    *result = this->data[index];
    return 0;
}

int addToEndOfDynamicArray(DynamicArray *this, dynamicContent_t value) {
    if (!this) {
        return 1;
    }

    if (this->actualSize == this->maxSize) {
        int newMaxSize = this->maxSize * 2;
        // FIXME: next line causes Segmentation Fault
        dynamicContent_t *newData = (dynamicContent_t *) realloc(this->data, sizeof(dynamicContent_t) * newMaxSize);
        if (!newData) {
            return 3;
        }
        this->data = newData;
        this->maxSize = newMaxSize;
    }

    this->data[this->actualSize] = value;
    this->actualSize++;
    return 0;
}

int getNumberOfElements(DynamicArray *this) {
    if (!this) {
        return 0;
    }
    return this->actualSize;
}
