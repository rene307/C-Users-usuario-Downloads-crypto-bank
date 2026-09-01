import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  IonButton, IonContent, IonHeader, IonIcon, IonInput, IonSegment,
  IonSegmentButton, IonSpinner, IonTitle, IonToolbar
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { arrowDown, arrowUp, flash, lockClosed, refresh, swapHorizontal } from 'ionicons/icons';
import { Asset, Portfolio, Quote, TradeType } from '../../models/trading';
import { TradingService } from '../../services/trading.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule, FormsModule, IonHeader, IonToolbar, IonTitle, IonContent,
    IonButton, IonIcon, IonInput, IonSegment, IonSegmentButton, IonSpinner
  ],
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit {
  portfolio?: Portfolio;
  asset: Asset = 'BTC';
  type: TradeType = 'BUY';
  amountClp = 100000;
  quote?: Quote;
  loading = false;
  message = '';
  error = '';

  constructor(private trading: TradingService) {
    addIcons({ arrowDown, arrowUp, flash, lockClosed, refresh, swapHorizontal });
  }

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.trading.portfolio().subscribe({
      next: data => {
        this.portfolio = data;
        this.refreshQuote();
      },
      error: () => this.error = 'No se pudo conectar con el backend Java.'
    });
  }

  selectAsset(asset: Asset): void {
    this.asset = asset;
    this.refreshQuote();
  }

  refreshQuote(): void {
    this.error = '';
    this.message = '';
    if (!this.amountClp || this.amountClp < 1000) {
      this.quote = undefined;
      return;
    }
    this.trading.quote(this.asset, this.type, Number(this.amountClp)).subscribe({
      next: data => this.quote = data,
      error: err => this.error = err?.error?.error ?? 'No fue posible calcular la operación.'
    });
  }

  execute(): void {
    this.loading = true;
    this.error = '';
    this.message = '';
    this.trading.trade(this.asset, this.type, Number(this.amountClp)).subscribe({
      next: () => {
        this.loading = false;
        this.message = this.type === 'BUY' ? 'Compra realizada correctamente.' : 'Venta realizada correctamente.';
        this.load();
      },
      error: err => {
        this.loading = false;
        this.error = err?.error?.error ?? 'No fue posible ejecutar la operación.';
      }
    });
  }

  setPreset(value: number): void {
    this.amountClp = value;
    this.refreshQuote();
  }

  clp(value?: number): string {
    return new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP', maximumFractionDigits: 0 }).format(value ?? 0);
  }

  crypto(value?: number): string {
    return (value ?? 0).toLocaleString('es-CL', { minimumFractionDigits: 0, maximumFractionDigits: 8 });
  }
}
