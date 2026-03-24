INSERT INTO vets VALUES (default, 'James', 'Carter');
INSERT INTO vets VALUES (default, 'Helen', 'Leary');
INSERT INTO vets VALUES (default, 'Linda', 'Douglas');
INSERT INTO vets VALUES (default, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (default, 'Henry', 'Stevens');
INSERT INTO vets VALUES (default, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (default, 'radiology');
INSERT INTO specialties VALUES (default, 'surgery');
INSERT INTO specialties VALUES (default, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (default, 'cat');
INSERT INTO types VALUES (default, 'dog');
INSERT INTO types VALUES (default, 'lizard');
INSERT INTO types VALUES (default, 'snake');
INSERT INTO types VALUES (default, 'bird');
INSERT INTO types VALUES (default, 'hamster');

INSERT INTO owners VALUES (default, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (default, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (default, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (default, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (default, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (default, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (default, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (default, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (default, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (default, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO pets VALUES (default, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets VALUES (default, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets VALUES (default, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets VALUES (default, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets VALUES (default, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets VALUES (default, 'George', '2010-01-20', 4, 5);
INSERT INTO pets VALUES (default, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (default, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (default, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets VALUES (default, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets VALUES (default, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets VALUES (default, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets VALUES (default, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits VALUES (default, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits VALUES (default, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits VALUES (default, 8, '2013-01-03', 'neutered');
INSERT INTO visits VALUES (default, 7, '2013-01-04', 'spayed');

INSERT INTO featured_dogs VALUES (default, 'Buddy', 'Golden Retriever', 3, 'Buddy loves fetch and swimming. He is the friendliest dog in the park!', 0);
INSERT INTO featured_dogs VALUES (default, 'Bella', 'Labrador Retriever', 2, 'Bella is gentle, patient, and great with children of all ages.', 1);
INSERT INTO featured_dogs VALUES (default, 'Max', 'German Shepherd', 4, 'Max is loyal, brave, and loves learning new tricks every day.', 2);
INSERT INTO featured_dogs VALUES (default, 'Luna', 'Siberian Husky', 1, 'Luna has striking blue eyes and boundless energy on every trail.', 3);
INSERT INTO featured_dogs VALUES (default, 'Charlie', 'Beagle', 5, 'Charlie has an incredible nose and a howl that wins hearts.', 4);
INSERT INTO featured_dogs VALUES (default, 'Daisy', 'French Bulldog', 3, 'Daisy is compact, charming, and loves lounging on the sofa.', 5);
INSERT INTO featured_dogs VALUES (default, 'Rocky', 'Rottweiler', 6, 'Rocky is a gentle giant who is endlessly devoted to his family.', 6);
INSERT INTO featured_dogs VALUES (default, 'Molly', 'Cocker Spaniel', 2, 'Molly has silky ears and an irresistible smile for everyone.', 7);
INSERT INTO featured_dogs VALUES (default, 'Bear', 'Bernese Mountain Dog', 4, 'Bear is calm, fluffy, and the ultimate cuddle companion.', 8);
INSERT INTO featured_dogs VALUES (default, 'Coco', 'Poodle', 3, 'Coco is clever, curly-coated, and always ready to perform.', 9);
INSERT INTO featured_dogs VALUES (default, 'Duke', 'Doberman Pinscher', 5, 'Duke is sleek, alert, and fiercely loyal to those he loves.', 10);
INSERT INTO featured_dogs VALUES (default, 'Rosie', 'Border Collie', 2, 'Rosie is a lightning-fast herder with an unstoppable work ethic.', 11);

INSERT INTO dog_photos VALUES (default, 1, 'https://placedog.net/640/480?id=1', 'Buddy in the park', 1, 204800, 'jpg');
INSERT INTO dog_photos VALUES (default, 1, 'https://placedog.net/640/480?id=2', 'Buddy fetching the ball', 2, 183200, 'jpg');
INSERT INTO dog_photos VALUES (default, 1, 'https://placedog.net/640/480?id=3', 'Buddy at the beach', 3, 220000, 'jpg');

INSERT INTO dog_photos VALUES (default, 2, 'https://placedog.net/640/480?id=4', 'Bella playing with kids', 1, 198400, 'jpg');
INSERT INTO dog_photos VALUES (default, 2, 'https://placedog.net/640/480?id=5', 'Bella in the garden', 2, 175000, 'jpg');
INSERT INTO dog_photos VALUES (default, 2, 'https://placedog.net/640/480?id=6', 'Bella portrait', 3, 195000, 'jpg');
INSERT INTO dog_photos VALUES (default, 2, 'https://placedog.net/640/480?id=7', 'Bella on a walk', 4, 210000, 'jpg');

INSERT INTO dog_photos VALUES (default, 3, 'https://placedog.net/640/480?id=8', 'Max on patrol', 1, 212000, 'jpg');
INSERT INTO dog_photos VALUES (default, 3, 'https://placedog.net/640/480?id=9', 'Max learning tricks', 2, 190000, 'jpg');
INSERT INTO dog_photos VALUES (default, 3, 'https://placedog.net/640/480?id=10', 'Max resting', 3, 200000, 'jpg');

INSERT INTO dog_photos VALUES (default, 4, 'https://placedog.net/640/480?id=11', 'Luna on a hike', 1, 225000, 'jpg');
INSERT INTO dog_photos VALUES (default, 4, 'https://placedog.net/640/480?id=12', 'Luna in the snow', 2, 235000, 'jpg');
INSERT INTO dog_photos VALUES (default, 4, 'https://placedog.net/640/480?id=13', 'Luna close-up', 3, 218000, 'jpg');
INSERT INTO dog_photos VALUES (default, 4, 'https://placedog.net/640/480?id=14', 'Luna playing', 4, 205000, 'jpg');
INSERT INTO dog_photos VALUES (default, 4, 'https://placedog.net/640/480?id=15', 'Luna at sunset', 5, 230000, 'jpg');

INSERT INTO dog_photos VALUES (default, 5, 'https://placedog.net/640/480?id=16', 'Charlie sniffing around', 1, 192000, 'jpg');
INSERT INTO dog_photos VALUES (default, 5, 'https://placedog.net/640/480?id=17', 'Charlie howling', 2, 188000, 'jpg');
INSERT INTO dog_photos VALUES (default, 5, 'https://placedog.net/640/480?id=18', 'Charlie portrait', 3, 197000, 'jpg');

INSERT INTO dog_photos VALUES (default, 6, 'https://placedog.net/640/480?id=19', 'Daisy on the sofa', 1, 176000, 'jpg');
INSERT INTO dog_photos VALUES (default, 6, 'https://placedog.net/640/480?id=20', 'Daisy in a sweater', 2, 184000, 'jpg');
INSERT INTO dog_photos VALUES (default, 6, 'https://placedog.net/640/480?id=21', 'Daisy portrait', 3, 179000, 'jpg');
INSERT INTO dog_photos VALUES (default, 6, 'https://placedog.net/640/480?id=22', 'Daisy at the park', 4, 190000, 'jpg');

INSERT INTO dog_photos VALUES (default, 7, 'https://placedog.net/640/480?id=23', 'Rocky at home', 1, 240000, 'jpg');
INSERT INTO dog_photos VALUES (default, 7, 'https://placedog.net/640/480?id=24', 'Rocky playing', 2, 232000, 'jpg');
INSERT INTO dog_photos VALUES (default, 7, 'https://placedog.net/640/480?id=25', 'Rocky portrait', 3, 228000, 'jpg');

INSERT INTO dog_photos VALUES (default, 8, 'https://placedog.net/640/480?id=26', 'Molly portrait', 1, 186000, 'jpg');
INSERT INTO dog_photos VALUES (default, 8, 'https://placedog.net/640/480?id=27', 'Molly running', 2, 193000, 'jpg');
INSERT INTO dog_photos VALUES (default, 8, 'https://placedog.net/640/480?id=28', 'Molly in the garden', 3, 200000, 'jpg');
INSERT INTO dog_photos VALUES (default, 8, 'https://placedog.net/640/480?id=29', 'Molly napping', 4, 175000, 'jpg');

INSERT INTO dog_photos VALUES (default, 9, 'https://placedog.net/640/480?id=30', 'Bear cuddling', 1, 255000, 'jpg');
INSERT INTO dog_photos VALUES (default, 9, 'https://placedog.net/640/480?id=31', 'Bear in the mountains', 2, 260000, 'jpg');
INSERT INTO dog_photos VALUES (default, 9, 'https://placedog.net/640/480?id=32', 'Bear portrait', 3, 248000, 'jpg');

INSERT INTO dog_photos VALUES (default, 10, 'https://placedog.net/640/480?id=33', 'Coco performing', 1, 185000, 'jpg');
INSERT INTO dog_photos VALUES (default, 10, 'https://placedog.net/640/480?id=34', 'Coco portrait', 2, 178000, 'jpg');
INSERT INTO dog_photos VALUES (default, 10, 'https://placedog.net/640/480?id=35', 'Coco playing', 3, 192000, 'jpg');
INSERT INTO dog_photos VALUES (default, 10, 'https://placedog.net/640/480?id=36', 'Coco in the park', 4, 196000, 'jpg');

INSERT INTO dog_photos VALUES (default, 11, 'https://placedog.net/640/480?id=37', 'Duke on guard', 1, 222000, 'jpg');
INSERT INTO dog_photos VALUES (default, 11, 'https://placedog.net/640/480?id=38', 'Duke portrait', 2, 215000, 'jpg');
INSERT INTO dog_photos VALUES (default, 11, 'https://placedog.net/640/480?id=39', 'Duke with family', 3, 230000, 'jpg');
INSERT INTO dog_photos VALUES (default, 11, 'https://placedog.net/640/480?id=40', 'Duke running', 4, 218000, 'jpg');
INSERT INTO dog_photos VALUES (default, 11, 'https://placedog.net/640/480?id=41', 'Duke resting', 5, 210000, 'jpg');

INSERT INTO dog_photos VALUES (default, 12, 'https://placedog.net/640/480?id=42', 'Rosie herding', 1, 202000, 'jpg');
INSERT INTO dog_photos VALUES (default, 12, 'https://placedog.net/640/480?id=43', 'Rosie portrait', 2, 195000, 'jpg');
INSERT INTO dog_photos VALUES (default, 12, 'https://placedog.net/640/480?id=44', 'Rosie in action', 3, 208000, 'jpg');
