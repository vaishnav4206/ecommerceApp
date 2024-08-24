export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageUrl: string;
    stockQuantity: number;
    createdAt: Date;
    updatedAt: Date;
    addedToCart?: boolean; 
}