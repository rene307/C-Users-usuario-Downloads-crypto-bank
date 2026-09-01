export type Asset = 'BTC' | 'ETH';
export type TradeType = 'BUY' | 'SELL';

export interface Movement {
  id: string;
  date: string;
  asset: Asset;
  type: TradeType;
  priceClp: number;
  amountClp: number;
  commissionClp: number;
  cryptoAmount: number;
}

export interface Portfolio {
  balanceClp: number;
  btc: number;
  eth: number;
  btcPriceClp: number;
  ethPriceClp: number;
  commissionRate: number;
  movements: Movement[];
}

export interface Quote {
  asset: Asset;
  type: TradeType;
  priceClp: number;
  amountClp: number;
  commissionClp: number;
  netClp: number;
  cryptoAmount: number;
}
