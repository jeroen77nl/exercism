/// <reference path="./global.d.ts" />
// @ts-check

/// <reference path="./global.d.ts" />
// @ts-check

export function cookingStatus(remainingTime) {
    switch (remainingTime) {
        case undefined:
            return 'You forgot to set the timer.'
        case 0:
            return 'Lasagna is done.';
        default:
            return 'Not done, please wait.';
    }
}

export function preparationTime(layers, avgPrepTime = 2) {
    return layers.length * avgPrepTime;
}

export function quantities(layers) {
    const NOODLE_PER_LAYER = 50;
    const SAUCE_PER_LAYER = 0.2;
    let result = {
        noodles: 0,
        sauce: 0
    }
    for (let layer of layers) {
        if (layer == 'noodles')
            result.noodles += NOODLE_PER_LAYER;
        else if (layer == 'sauce')
            result.sauce += SAUCE_PER_LAYER;
    }
    return result;
}

export function addSecretIngredient(friendsList, myList) {
    let secretItem = friendsList[friendsList.length - 1];
    myList.push(secretItem);
}

export function scaleRecipe(recipe, portions) {
    const factor = portions / 2;
    let result = {};
    for (let ingredient in recipe) {
        result[ingredient] = recipe[ingredient] * factor;
    }
    return result;
}