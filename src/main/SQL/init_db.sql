create table users
(
    id serial not null,
    first_name varchar not null,
    last_name varchar not null,
    country varchar not null,
    address varchar not null,
    email varchar not null,
    password varchar not null,
    salt varchar not null
);

create unique index users_id_uindex
    on users (id);

alter table users
    add constraint users_pk
        primary key (id);

create table orders
(
    id serial not null,
    user_id int not null,
    date date not null,
    constraint orders_users__fk
        foreign key (user_id) references users
);

create unique index orders_id_uindex
    on orders (id);

alter table orders
    add constraint orders_pk
        primary key (id);

create table products
(
    id serial not null,
    name varchar,
    description varchar,
    price int not null
);

create unique index products_id_uindex
    on products (id);

alter table products
    add constraint products_pk
        primary key (id);

create table items
(
    id serial not null,
    product_id int not null,
    quantity int not null,
    constraint items_products__fk
        foreign key (product_id) references products
);

create unique index items_id_uindex
    on items (id);

alter table items
    add constraint items_pk
        primary key (id);

create table carts
(
    id serial not null,
    user_id int not null,
    sum_price int,
    constraint carts_users__fk
        foreign key (user_id) references users
);

create unique index carts_id_uindex
    on carts (id);

alter table carts
    add constraint carts_pk
        primary key (id);

alter table items
    add cart_id int;

alter table items
    add constraint items_carts__fk
        foreign key (cart_id) references carts;

alter table orders
    add cart_id int;

alter table orders
    add constraint orders_carts__fk
        foreign key (cart_id) references carts;

create table categories
(
    id serial not null,
    name text not null,
    description text not null
);

create unique index categories_id_uindex
    on categories (id);

alter table categories
    add constraint categories_pk
        primary key (id);

create table suppliers
(
    id serial not null,
    name text not null
);

create unique index suppliers_id_uindex
    on suppliers (id);

alter table suppliers
    add constraint suppliers_pk
        primary key (id);

alter table products
    add category_id int;

alter table products
    add supplier_id int;

alter table products
    add constraint products_categories__fk
        foreign key (category_id) references categories;

alter table products
    add constraint products_suppliers__fk
        foreign key (supplier_id) references suppliers;

INSERT INTO categories(name, description)
VALUES
('Big cat', 'Between 40-300 kg'),
('Mid-size cat', 'Between 15-40 kg'),
('Small cat', 'Up to 15 kg');

INSERT INTO suppliers(name)
VALUES
('Africa'),
('Asia'),
('Europe'),
('North-America'),
('South-America');

INSERT INTO products(name, price, description, category_id, supplier_id)
VALUES
('Tiger (Panthera tigris)', 99999, 'Tigers are perhaps the most recognisable of all the cats. They typically have rusty-reddish to brown-rusty coats, a whitish medial and ventral area, a white fringe that surrounds the face, and stripes that vary from brown or gray to pure black. Native to much of eastern and southern Asia, the tiger is an apex predator and an obligate carnivore. Reaching up to 4 metres (13 ft) in total length and weighing up to 300 kilograms (660 pounds)', 1, 2),
('Lion (Panthera Leo)',119999, 'The lion is one of four big cats (genus Panthera). After the tiger, it is the second largest living cat in the world. Wild lions currently exist in Sub- Saharan Africa and a critically endangered remnant population in northwest India (Gir forest), having disappeared from North Africa, the Middle East, and Western Asia in historic times.',1,1),
('Leopard or panther (Panthera Pardus)',84999,'The leopard is the smallest of the four big cats in the genus Panthera. In Africa it is called leopard and in Asia panther. It is the most common big cat that can adapt easily to differetn habitats and circumstances. It is an opportunistic hunter and known for its climbing ability. It has been observed resting on tree branches during the day and descending from trees headfirst. It is a powerful swimmer, although not as strong as some other big cats, such as the tiger.',1,1),
('Jaguar (Panthera Onca)',89999,'The jaguar is one of the big cats in the Panthera genus.. It is the only Panthera found in the Americas. The jaguar is the third-largest big cat (feline) after the tiger and the lion, and the largest and most powerful feline in the Western Hemisphere. The jaguar’s present range extends from Mexico across much of Central America and south to Paraguay and northern Argentina.',1,5),
('Cheetah (Acinonyx Jubatus)',84999,'The cheetah is an atypical member of the cat family that is unique in its speed, while lacking climbing abilities. As such, it is placed in its own genus, Acinonyx. It is the fastest land animal, reaching speeds between 112 and 120 km/h (70 and 75 mph) in short bursts covering distances up to 460 m (1,500 ft), and has the ability to accelerate from 0 to 110 km/h (68 mph) in three seconds, faster than most racing cars.',1,1),
('Puma (Puma Concolor)',79999,'The cougar (Puma concolor), also puma, mountain lion, cool cat, or panther, depending on region, is a mammal of the Felidae family, native to the Americas. Close relative to the cheetah and the jaguarundi. This large, solitary cat has the greatest range of any wild terrestrial mammal in the Western Hemisphere, extending from Yukon in Canada to the southern Andes of South America. An adaptable, generalist species, the cougar is found in every major American habitat type.',1,4),
('Lynx (Lynx Lynx)',48999,'A lynx is any of four medium-sized wild cats. All are members of the genus Lynx, but there is considerable confusion about the best way to classify felids at present, and some authorities classify them as part of the genus Felis. Lynx are usually solitary, although a small group of lynx may travel and hunt together occasionally. They feed on a wide range of animals from Reindeer, Roe Deer, small Red Deer, and Chamois, to smaller, more usual prey: birds, and small mammals, like snowshoe hares, fish, sheep, and goats.',2,3),
('Bobcat (Lynx Rufus)',41999,'The Bobcat, or red lynx, (Lynx rufus) with his grey to brown coat, whiskered face and black-tufted ears resembles the other species of the mid-sized Lynx genus, though it is smaller than the Canada Lynx, with which it shares parts of its range. It is about twice as large as the domestic cat and it derives its name from the black-tipped stubby tail.',2,4),
('Clouded leopard (Neofelis Nebulosa)',54999,'A lot of people consider this mysterious medium-sized cat as the most beautiful one of the family of felines. The Clouded Leopard is found in Southeast Asia and it seems to be a cross between a big cat and a small cat. Remarkably little is known about it and it is assumed that its primary prey includes arboreal and terrestrial mammals, particularly gibbons, macaques, and civets supplemented by other small mammals, deer, birds, porcupines, and domestic livestock.',2,2),
('Caracal (Caracal Caracal)',44999,'Caracal means black ears in Turkish. Large, tapering ears with five cm erect tufts of black hair, used for communication, are probably the most unique feature of this cat. Essentially an animal of dry regions, the caracal has a wide habitat tolerance. They are found in woodlands, savannahs and acacia scrub throughout Africa, jungle scrub and deserts in India, and arid, sandy regions and steppes in Asia.',2,2),
('Ocelot (Leopardus Pardalis)',19999,'The ocelot is a wild cat, of the group small cats, distributed over South and Central America and Mexico. The ocelot’s appearance is similar to the domestic cat, though its fur resembles that of a Jaguar or a clouded leopard and was once regarded as particularly valuable. As a result, hundreds of thousands of ocelots have been killed for their fur. The feline was clasified a vulnerable endangered species from 1972 until 1996, but is now rated least concern by the 2008 IUCN Red List.',3,5),
('Oncilla (Leopardus tigrinus)',19999,'The Oncilla (Leopardus tigrinus) or Tiger Cat, is a small spotted felid found in the tropical rainforests of Central and South America. It is a close relative of the Ocelot and the Margay, and has a rich ochre coat, spotted with black rosettes. The Oncilla is a nocturnal animal,but in areas such as Caatinga, where their main food source consists of diurnal lizards, they are more likely to be active during the day.',3,5),
('Snow leopard (Uncia Uncia)',48999,'The snow leopard (Uncia uncia) is a moderately large cat native to the mountain ranges of South Asia and Central Asia. Snow leopards live between 3,000 and 5,500 metres (9,800 and 18,000 ft) above sea level in the rocky mountain ranges of Central Asia. Their secretive nature means that their exact numbers are unknown, but it has been estimated that between 3,500 and 5,000 snow leopards exist in the wild and between 600 and 700 in zoos worldwide.',2,2),
('Serval (Leptailurus Serval)',44999,'The Serval is a medium-sized African wild cat. It is closely related to the African Golden Cat and the Caracal. It is a slender animal, with long legs and a fairly short tail. The head is small in relation to the body, and the tall, oval ears are set close together. The pattern of the fur is variable.',2,1),
('Sand cat (Felis Margarita)',19999,'The Sand cat is a small African wild cat. Not the smallest as this is the closely related Blackfooted cat. Often is referred to “desert cat” but this name is reserved for Felis silvestris lybica, or the African wildcat, but it could be appropriate for this species. It lives in those arid areas that are too hot and dry even for the desert cat: the Sahara, the Arabian Desert, and the deserts of Iran and Pakistan.',3,1),
('Rusty-spotted cat (Prionailurus Rubiginosus)',14999,'The Rusty-spotted Cat rivals (and may exceed) the South African Black footed cat (Felis nigripes) as the world’s smallest wild cat. Being one of the lesser studied South Asian carnivores it has been listed as vulnerable by IUCN only in 2002. Rusty-spotted cats have a relatively restricted distribution. They mainly occur in moist and dry deciduous forests as well as scrub and grassland in India and Sr Lanka, but are likely absent from evergreen forest. They prefer dense vegetation and rocky areas.',3,2),
('Colocolo (Oncifelis Colocolo)',18999,'Pampas cats are widely distributed and tolerant of altered habitat. International trade in their pelts ceased in 1987. Because of the large range of these cats, their status varies from endangered in Peru, rare in Paraguay and status unknown in Brazil.',3,5),
('Marbled cat (Pardofelis Marmorata)',49999,'The range of the Marbled Cat extends from Northeast India, with subspecies in Nepal, and through Southeast Asia including Borneo and Sumatra, linked to the mainland of Asia. The forest provide the Marbled Cat with much of its prey: birds, squirrels, other rodents and reptiles. It is rarely sighted in its densely forested habitat and so little studied or understood.',2,2),
('Manul (Otocolobus manul)',13999,'The Pallas cat, named after German naturalist Pyotr Simon Pallas (1741-1811) is a small-sized wild cat, also known as the Manul. The Pallas’ cat is adapted to cold arid environments and lives in rocky terrain and grasslands through out central Asia and parts of Eastern Europe. It’s known for its flattened face, stocky build, and long hair.',3,2),
('Margay (Leopardus Wiedii)',15999,'The Margay is a spotted cat native to Central and South America. It roams the rainforests from Mexico to Argentina. The margay is similar in appearance to the Ocelot, though itïs body is smaller, growing up two 25-27 inches and in comparison with the Ocelot, the margay displays longer legs and tail. Most notably the Margay is a much more skillful climber than its relative, and it is sometimes called the Tree Ocelot because of this skill.',3,5),
('Jungle cat (Felis Chaus)',12999,'The jungle cat (“swamp lynx”), is a medium-small cat and today considered the largest remaining species of the wild cat genus Felis. Jungle cats are the most common small cats in India and are also found in Egypt, West and Central Asia, South Asia, Sri Lanka and Southeast Asia. Jungle cats are solitary in nature. Their habitat consists of other animals’ abandoned burrows, tree holes, and humid coves under swamp rocks.',3,2),
('Jaguarondi (Herpailurus Yagouaroundi)',39999,'The jaguarundi (Herpailurus Yaguarondi) is a medium-sized wild cat. Not related to the jaguar, though the name seems to say otherwise, but it’s closely related to the cougar (puma) and also to the cheetah. Its habitat is lowland brush areas close to a source of running water. It occasionally inhabits dense tropical areas as well.',2,5),
('Iriomotecat (Prionailurus Bengalensis Iriomotensis)',19999,'The Iriomote cat is a subspecies of the Leopard cat that lives exclusively on the Japanese island of Iriomote. It has been classified as Critically Endangered by IUCN since 2008, as the population size is fewer than 250, is declining, and consists of a single subpopulation. As of 2007, there are an estimated 100–109 individuals remaining.',3,2),
('Iberian Lynx (Lynx pardinus)',59999,'The Iberian lynx is a wildcat native to the Iberian Peninsula in southwestern Europe that is listed as endangered on the IUCN Red List. It preys almost exclusively on the European rabbit.',2,3),
('Geoffroy''s cat (Oncifelis Geoffroyi)',17999,'Geoffroy’s Cat is probably the most common wild cat in South America. Although it appears to be plentiful, some conservationists are concerned because Geoffroy’s Cat is hunted extensively for its pelt. The conservation status is near threatened. Geoffroy’s Cat is about the size of a domestic cat. Its fur has black spots, but the background color varies from region to region – in the north, a brownish yellow coat is most common. Farther south, the coat is grayish.',3,5),
('Flat-headed cat (Prionailurus Planiceps)',13999,'The Flat-headed cat is a small cat from forested areas, closely related to the Fishing cat and thanking his name to the flat-shaped head. The skull is fairly long, while the skull roof, as suggested by both its common and scientific name, is rather flat. It is considered endangered by IUCN due to habitat loss and pollution.',3,2),
('Fishing cat (Prionailurus Viverrinus)',39999,'The Fishing Cat (Prionailurus viverrinus) is a medium-sized cat whose disjunct global range extends from eastern Pakistan through portions of India, Nepal and Sri Lanka, throughout Bangladesh and Mainland Southeast Asia to Sumatra and Java. The closest relative is the Flat-headed cat.',2,2),
('Wildcat (Felis silvestris silvestris)',16999,'The European Wildcat (Felis silvestris silvestris) is a subspecies of the wildcat that inhabits forests of Western, Central, Eastern and Southern Europe, as well as Scotland, Turkey and the Caucasus Mountains, it has been extirpated from Scandinavia, England and Wales.',3,3),
('Chinese mountain cat (Felis Bieti)',12999,'The Chinese Mountain Cat (Felis bieti), also known as the Chinese Desert Cat, is a small wild cat of western China. It is the least known member of the genus Felis and it is more likely a subspecies of Felis silvestris (then called Felis silvestris bieti).',3,2),
('Kodkod (Oncifelis Guigna)',19999,'The Chilean cat or Kodkod is the smallest wild cat of South America and rival the Black Footed Cat (Africa) and the Rusty Spotted Cat (Asia) as the smallest felines in the world. They are quite similar in appearance to Geoffroy’s Cat (Oncifelis Geoffoyi) with which they share their habitat, but they are smaller and do have a smaller face and thicker tail.',3,5),
('Black-footed cat (Felis Nigripes)',13999,'The black-footed cat (Felis nigripes) is a small wild cat distributed over South Africa, Namibia, Botswana and Zimbabwe. The habitats of this cat species are arid semi-desert and savannah. The black-footed cat is a solitary animal and is active at night and thus rarely seen. In the daytime it hides in springhare burrows, under rock slabs and shrubs, and within hollow termite mounds.',3,1),
('Asian Leopard Cat (Prionailurus Bengalensis)',18999,'The Asian Leopard Cat is a small wild cat and has the widest geographic distribution of all felines. It can be found in forest areas throughout Indonesia, Philippines, Borneo, Malaysia, Thailand, Myanmar, Laos, Cambodia, China and Taiwan. Their range of habitat is varied, and includes tropical forest, scrubland, pine forest, second-growth woodland, semi-desert, and agricultural regions, especially near water sources, and may be found at heights up to 3000 m.',3,2),
('Asian golden cat (Catopuma Temminckii)',45999,'The Asian Golden Cat lives throughout Southeast Asia, ranging from Tibet and Nepal to Southern China, India, and Sumatra. It prefers forest habitats interspersed with rocky areas, and is found in deciduous, subtropical evergreen, and tropical rainforests. The Asian Golden Cat is sometimes found in more open terrain. It ranges from the lowlands to altitudes of up to 3000 meters in the Himalayas.',2,2),
('African wild cat (Felis Silvestris Libyca)',17999,'The African wildcat (Felis silvestris lybica), is a subspecies of the wildcat (F. silvestris). They appear to have diverged from the other subspecies about 131,000 years ago. About 10,000 years ago some African Wildcats were domesticated in the Middle East and they are the ancestors of the domestic cat.',3,1),
('African golden cat (Profelis Aurata)',45999,'The African Golden Cat (Profelis aurata) is a medium-sized wild cat distributed over the rainforests of West and Central Africa. Due to its extremely hidden living style, not much is known about this cat’s behaviour.',2,1);