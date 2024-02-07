
export class CartEntry{
    prodId: number;
    name: string;
    image: string;
    quantity: number;
    price: number;
    total: number;

    constructor(id: number, name:string, image:string, quantity:number, price:number) {
        this.prodId = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.total = quantity * price;
    }
}