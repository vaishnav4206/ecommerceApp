export interface CartItem {
    id?: number;
    userId: string | null;
    productId: number;
    productName: string;
    quantity: number;
    price: number;
    imageUrl?: string;
}