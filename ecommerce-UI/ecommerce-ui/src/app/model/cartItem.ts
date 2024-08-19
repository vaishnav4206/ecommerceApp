export interface CartItem {
    id?: number;
    userId: string;
    productId: number;
    productName: string;
    quantity: number;
    price: number;
    imageUrl?: string;
}