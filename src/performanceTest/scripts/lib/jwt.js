import crypto from "k6/crypto";
import encoding from "k6/encoding";
import config from "./config.js";

// from https://github.com/grafana/k6/blob/master/examples/jwt.js

const algToHash = {
    HS256: "sha256",
    HS384: "sha384",
    HS512: "sha512"
};

function sign(data, hashAlg, secret) {
    let hasher = crypto.createHMAC(hashAlg, secret);
    hasher.update(data);

    return hasher.digest("base64").replace(/\//g, "_").replace(/\+/g, "-").replace(/=/g, "");
}

export function encode(userid) {
    const NOW = Math.floor(Date.now() / 1000)
    const algorithm = "HS256";
    let header = encoding.b64encode(JSON.stringify({ alg: algorithm }), "rawurl");
    let payload = {
        sub: `${userid}`,
        iss: config.JWT_ISSUER,
        iat: NOW,
        exp: NOW + (60 * 60),
    }
    payload = encoding.b64encode(JSON.stringify(payload), "rawurl", "s");
    let sig = sign(header + "." + payload, algToHash[algorithm], config.JWT_RAW_SECRET);
    return [header, payload, sig].join(".");
}