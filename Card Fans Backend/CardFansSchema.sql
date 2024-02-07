
CREATE TABLE public.products (
	product_id serial4 NOT NULL,
	product_name varchar(20) NOT NULL,
	suit_colors varchar NOT NULL,
	face_type varchar NOT NULL,
	theme varchar NOT NULL,
	background varchar NOT NULL,
	price numeric NOT NULL,
	description varchar NULL,
	quantity int4 NOT NULL,
	picture bytea NULL,
	CONSTRAINT products_pk PRIMARY KEY (product_id)
);

CREATE TABLE public.users (
	id serial4 NOT NULL ,
	user_name varchar(15) NOT NULL,
	first_name varchar(15) NOT NULL,
	last_name varchar(20) NOT NULL,
	password varchar(30) NOT NULL,	
	address_line_1 varchar(20)  NULL,
	city varchar(10) NULL,
	state varchar(10)  NULL,
	zip_code varchar NULL,
	email varchar(30) NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX users_user_email_idx ON public.users USING btree (email);
CREATE UNIQUE INDEX users_user_name_idx ON public.users USING btree (user_name);

CREATE TABLE public.orders (
	order_id serial4 NOT NULL,
	order_date time NOT NULL,
	order_status int4 NOT NULL,
	user_id serial4 NOT NULL,
	CONSTRAINT orders_pk PRIMARY KEY (order_id),
	CONSTRAINT orders_fk FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE TABLE public.order_details (
	order_detail_id serial4 NOT NULL,
	order_id serial4 NOT NULL,
	product_id serial4 NOT NULL,
	quantity int4 NOT NULL,
	CONSTRAINT order_detail_pk PRIMARY KEY (order_detail_id),
	CONSTRAINT order_detail_fk FOREIGN KEY (order_id) REFERENCES public.orders(order_id),
	CONSTRAINT order_detail_product_fk FOREIGN KEY (product_id) REFERENCES public.products(product_id)
);

CREATE TABLE IF NOT EXISTS public.authority (
  id serial4 NOT NULL,
  name VARCHAR(45) NOT NULL,
  user_id serial4 NOT NULL,
 CONSTRAINT authority_pk PRIMARY KEY (id),
 CONSTRAINT authority_user_fk FOREIGN KEY (user_id) REFERENCES public.users(id)
  );

INSERT INTO public.products
(product_name, suit_colors, face_type, theme, price, background,description, quantity)
VALUES ('Aviary','red, black','standard','birds',5.99,'standard','Bird lovers from all over flock together to find a copy of this lovely deck',17),
('Fyrebird','red, black','fancy','birds, myth',6.99,'fancy','This deck is a real hot commodity with phoenixes gracing the back and the suit symbols set ablaze',450),
('Dark Mode','blue, green','standard','tech',6.99,'standard','Do white background cards burn your eyes? If so then do we have a deck for you!',56),
('Dragon','red, black','fancy','myth',5.99,'fancy','Mythical beasts of majesty dawn the backs of these gorgeous cards',77),
('Riderback - red','red, black','standard','cards',4.99,'standard','An all-time classic',100),
('Riderback - blue','red, black','standard','cards',4.99,'standard','An all-time classic',100),
('Stargazer','same','standard','space',5.99,'standard','Lovers of the stars unite! These cards will let you see far into the reaches of outer space any time of day',33),
('Stargazer: Sunspot','same','standard','space',5.99,'standard','Unlike the actual sun, you  can stare at these beauties all day and still retain your sight',27),
('Avengers','red, black','fancy','tv/movies',7.99,'fancy','Every marvel fans favorite deck',15);



INSERT INTO public.users
(user_name, first_name, last_name, "password",  address_line_1, city, state, zip_code, email)
VALUES('abe', 'john', 'smith', 'secret',  '123 street st', 'new york', 'new york', '32456', 'a@gmail.com');


INSERT  INTO public.authority ( name, user_id) VALUES ('ROLE_USER ', 1);
INSERT  INTO public.authority ( name, user_id) VALUES ('ROLE_ADMIN', 1);
INSERT  INTO public.authority ( name, user_id) VALUES ('ROLE_EMPLOYEE', 1);
INSERT  INTO public.authority ( name, user_id) VALUES ('ROLE_GUEST', 1);
