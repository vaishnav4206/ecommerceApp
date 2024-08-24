export interface Order {
    id: number;
    userId: string | null;
    productIds: number[];
    totalAmount: number;
    status: string;
    rushDelivery: boolean;
    createdAt: Date;
    updatedAt: Date;
}