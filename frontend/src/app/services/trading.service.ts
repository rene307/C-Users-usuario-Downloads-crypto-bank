import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asset, Portfolio, Quote, TradeType } from '../models/trading';

@Injectable({ providedIn: 'root' })
export class TradingService {
  private readonly api = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  portfolio(): Observable<Portfolio> {
    return this.http.get<Portfolio>(`${this.api}/portfolio`);
  }

  quote(asset: Asset, type: TradeType, amountClp: number): Observable<Quote> {
    return this.http.post<Quote>(`${this.api}/quote`, { asset, type, amountClp });
  }

  trade(asset: Asset, type: TradeType, amountClp: number): Observable<unknown> {
    return this.http.post(`${this.api}/trade`, { asset, type, amountClp });
  }
}
