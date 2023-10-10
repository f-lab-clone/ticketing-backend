
function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
}

const getPrefix = () => {
    return `K6-${uuid().substring(0, 10)}`;
}

const User = (ID) => {
    if (ID) {
        let pw = Number(ID) % 1000
        if (pw == 0) pw = 1000
        return {
            name: `${ID}-name`,
            email: `K6-${ID}@email.com`,
            password: `K6-${pw}-password`,
            phoneNumber: '010-1234-1234',
        }
    } else {
        return {
            name: `${getPrefix()}-name`,
            email: `${getPrefix()}@email.com`,
            password: `${getPrefix()}-password`,
            phoneNumber: '010-1234-1234',
        }
    }
}

const Reservation = (eventId) => {
    return {
        eventId,
        name: "name",
        phoneNumber: "010-1234-1234",
        postCode: 12345,
        address: "address",
    }
}

export default {
    User,
    Reservation,
}