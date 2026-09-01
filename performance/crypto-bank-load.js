import { check } from 'k6';
import http from 'k6/http';

export const options = {
  vus: 10,
  duration: '20s',

  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
    checks: ['rate>0.95'],
  },
};

export default function () {

  const url = 'http://localhost:8080/api/quote';

  const payload = JSON.stringify({
    asset: 'BTC',
    type: 'BUY',
    amountClp: 10000
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = http.post(url, payload, params);

  check(response, {
    'HTTP 200': (r) => r.status === 200,
    'latencia menor a 500 ms': (r) => r.timings.duration < 500,
  });
}