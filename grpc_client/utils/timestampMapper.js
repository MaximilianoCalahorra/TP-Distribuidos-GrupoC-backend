/**
 * Convierte un string "YYYY-MM-DDTHH:mm" a un objeto literal compatible con protoLoader.
 * @param {string} datetimeStr - fecha y hora del input
 * @returns {{seconds: number, nanos: number}}
 */
export function parseDatetimeToProto(datetimeStr) {
  if (!datetimeStr) throw new Error('La fecha y hora no puede ser nula');

  const date = new Date(datetimeStr);
  return {
    seconds: Math.floor(date.getTime() / 1000),
    nanos: date.getMilliseconds() * 1e6,
  };
}