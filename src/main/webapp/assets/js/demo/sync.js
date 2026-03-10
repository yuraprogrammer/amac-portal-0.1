const channel = new BroadcastChannel("classroom-demo");

const Sync = {

    send(type, payload) {
        channel.postMessage({
            type: type,
            payload: payload
        });
    },

    listen(handler) {
        channel.onmessage = (event) => {
            handler(event.data);
        };
    }

};