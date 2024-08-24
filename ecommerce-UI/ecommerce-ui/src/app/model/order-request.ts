export interface OrderRequest {
    userId: string | null;
    productIds: number[];
    totalAmount: number;
    rushDelivery: boolean;
}