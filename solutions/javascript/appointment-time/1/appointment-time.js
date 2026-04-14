// @ts-check

/**
 * Create an appointment
 *
 * @param {number} days
 * @param {number} [now] (ms since the epoch, or undefined)
 *
 * @returns {Date} the appointment
 */
export function createAppointment(days, now = undefined) {
  let nu = now === undefined ? new Date() : new Date(now);
  const millisecondsToAdd = days * 24 * 60 * 60 * 1000;
  return new Date(nu.getTime() + millisecondsToAdd);
}

/**
 * Generate the appointment timestamp
 *
 * @param {Date} appointmentDate
 *
 * @returns {string} timestamp
 */
export function getAppointmentTimestamp(appointmentDate) {
  return appointmentDate.toISOString();
}

/**
 * Get details of an appointment
 *
 * @param {string} timestamp (ISO 8601)
 *
 * @returns {Record<'year' | 'month' | 'date' | 'hour' | 'minute', number>} the appointment details
 */
export function getAppointmentDetails(timestamp) {
  let date = new Date(timestamp);
  let result = {
    year: date.getFullYear(),
    month: date.getMonth(),
    date: date.getDate(),
    hour: date.getHours(),
    minute: date.getMinutes(),
  };
  return result;
}

/**
 * Update an appointment with given options
 *
 * @param {string} timestamp (ISO 8601)
 * @param {Partial<Record<'year' | 'month' | 'date' | 'hour' | 'minute', number>>} options
 *
 * @returns {Record<'year' | 'month' | 'date' | 'hour' | 'minute', number>} the appointment details
 */
export function updateAppointment(timestamp, options) {
  let date = new Date(timestamp);
  for (let key in options) {
    let value = options[key];
    switch (key) {
      case 'year':
        date.setFullYear(value);
        break;
      case 'month':
        date.setMonth(value);
        break;
      case 'date':
        date.setDate(value);
        break;
      case 'hour':
        date.setHours(value);
        break;
      default:
        date.setMinutes(value);
    }
  }
  return getAppointmentDetails(date.toISOString());
}

/**
 * Get available time in seconds (rounded) between two appointments
 *
 * @param {string} timestampA (ISO 8601)
 * @param {string} timestampB (ISO 8601)
 *
 * @returns {number} amount of seconds (rounded)
 */
export function timeBetween(timestampA, timestampB) {
  let d1 = new Date(timestampA);
  let d2 = new Date(timestampB);
  let verschilMs = d1.getTime() - d2.getTime();
  let verschilSec = Math.round(verschilMs / 1000);
  return Math.abs(verschilSec);
}

/**
 * Get available times between two appointment
 *
 * @param {string} appointmentTimestamp (ISO 8601)
 * @param {string} currentTimestamp (ISO 8601)
 */
export function isValid(appointmentTimestamp, currentTimestamp) {
  return new Date(appointmentTimestamp).getTime()
    > new Date(currentTimestamp).getTime();
}
