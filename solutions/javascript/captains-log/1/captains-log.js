// @ts-check

/**
 * Generates a random starship registry number.
 *
 * @returns {string} the generated registry number.
 */
export function randomShipRegistryNumber() {
  return `NCC-${getRandomIntInRange(1000, 9999)}`;
}

function getRandomIntInRange(min, max) {
  return Math.floor(min + Math.random() * (max - min));
}

function getRandomFloatInRange(min, max) {
  return min + Math.random() * (max - min);
}

/**
 * Generates a random stardate.
 *
 * @returns {number} a stardate between 41000 (inclusive) and 42000 (exclusive).
 */
export function randomStardate() {
  return getRandomFloatInRange(41000, 42000)
}

/**
 * Generates a random planet class.
 *
 * @returns {string} a one-letter planet class.
 */
export function randomPlanetClass() {
  const planetClasses = ['D', 'H', 'J', 'K', 'L', 'M','N', 'R', 'T', 'Y'];

  let randomIndex = getRandomIntInRange(0, planetClasses.length);
  return planetClasses[randomIndex];
}
