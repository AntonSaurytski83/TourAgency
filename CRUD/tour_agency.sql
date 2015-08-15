
CREATE DATABASE  IF NOT EXISTS `tour_agency` DEFAULT CHARSET=utf8 /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tour_agency`;


SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `tour_agency`
--

-- --------------------------------------------------------

--
-- Структура таблицы `client_info`
--

CREATE TABLE IF NOT EXISTS `client_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `is_regular` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `fk_client_info_1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Дамп данных таблицы `client_info`
--

INSERT INTO `client_info` (`id`, `user_id`, `is_regular`) VALUES
(1, 4, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`rolename`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Дамп данных таблицы `role`
--

INSERT INTO `role` (`id`, `rolename`) VALUES
(2, 'admin'),
(1, 'client');

-- --------------------------------------------------------

--
-- Структура таблицы `tour`
--

CREATE TABLE IF NOT EXISTS `tour` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tourname` varchar(255) NOT NULL,
  `type` int(1) NOT NULL,
  `details` text NOT NULL,
  `price` int(11) NOT NULL,
  `hot` int(1) DEFAULT '0',
  `regular_discount` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Дамп данных таблицы `tour`
--

INSERT INTO `tour` (`id`, `tourname`, `type`, `details`, `price`, `hot`, `regular_discount`) VALUES
(1, 'Горящий тур в Дубай на 7 дней', 1, 'Дубай – второй по величине эмират с одноименной столицей – растянулся вдоль берега Персидского Залива на 72 км. Археологические раскопки свидетельствуют о том, что рыбацкие поселения существовали на месте города Дубай более 4000 лет назад. В прошлом – небольшая прибрежная деревня, на сегодняшний день Дубай – это современный город, имеющий свой неповторимый облик. Расположившийся на берегах залива Крик, город разделен на две части: восточную Дейру и западный район – Бар Дубай. Иногда Дубай называют «Парижем Персидского Залива», ведь это главный деловой центр Ближнего Востока, один из крупнейших центров международной торговли и туризма, сердце коммерческой жизни государства', 999, 1, 5),
(2, 'Шопинг тур в Милан', 2, 'Милан — деловой и промышленный центр Италии, а также законодатель европейской моды — это еще одно лицо многоликой Италии.  Сердце Милана — Пьяцца Дуомо. На этой огромной площади миланцы вместе встречают Рождество, Новый год и прочие шумные праздники. Главным сооружением на площади является Миланский Собор (Собор Дуомо), устремляющий ввысь легкие ажурные кружева готики. Неподалеку находится знаменитый оперный театр "Ла Скала", место испытания и посвящения в мир музыки композиторов, певцов и дирижеров с мировыми именами.', 1448, 0, 6),
(7, 'Экскурсии на Гоа', 1, 'Во время этой экскурсии Вы сможете посетить большинство исторических и культурных памятников ГОА, датируемых XVI веком, памятников времен португальского владычества. Сначала Вы увидите самый первый собор, построенный португальцами в ГОА. Его строительство было начато в 1510 году, в честь празднования взятия Старого Гоа в день Св. Катерины, а окончательно завершено только через 109 лет. Проход длиной 80 метров заканчивается резным позолоченным алтарем, одним из самых красивых в Индии. В соборе есть купель индусского происхождения. Кафедральный собор святой Екатерины славен своими колоколами. Один из них, «Золотой колокол», входит в число лучших в мире. ', 975, 1, 0),
(8, 'Экскурсия по парижу', 3, 'Самостоятельная организация поездки имеет множество преимуществ, как по стоимости, так и по неповторимости программы, однако, требует больших усилий, и советы менеджеров нашего Парижского офиса могут оказаться весьма полезными! Мы не знаем, что побудило Вас к поездке в Париж: давнее желание побывать в этом чудесном городе или любовь к новым открытиям, но в меру своих сил мы поможем каждому из Вас увидеть именно Ваш неповторимый Париж! Кто-то пожелает обойти все Парижские музеи, кому-то захочется посвятить поездку кухне лучших ресторанов, или, возможно, появится желание отправиться в недельную поездку по самым красивым деревушкам Франции?', 502, 0, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `tour_purchase`
--

CREATE TABLE IF NOT EXISTS `tour_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tour_id` int(11) NOT NULL,
  `paid` varchar(255) NOT NULL,
  `client_id` int(11) NOT NULL,
  `amount` float NOT NULL,
  `purchase_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_TOUR_idx` (`tour_id`),
  KEY `FK_CLIENT_idx` (`client_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=22 ;

--
-- Дамп данных таблицы `tour_purchase`
--

INSERT INTO `tour_purchase` (`id`, `tour_id`, `paid`, `client_id`, `amount`, `purchase_date`) VALUES
(18, 1, '0', 3, 999, '2015-05-28 00:00:00'),
(19, 1, '1', 3, 999, '2015-05-28 00:00:00'),
(20, 2, '0', 3, 1448, '2015-05-28 00:00:00'),
(21, 2, '0', 3, 1448, '2015-05-28 00:00:00');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `FK_ROLE_ID_idx` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role_id`) VALUES
(2, 'admin', '21232f297a57a5a743894a0e4a801fc3', 2),
(3, 'client', '62608e08adc29a8d6dbc9754e659f125', 1),
(4, 'jack', '4ff9fc6e4e5d5f590c4f2134a8cc96d1', 1),
(5, 'client1', '62608e08adc29a8d6dbc9754e659f125', 1),
(6, 'client2', '62608e08adc29a8d6dbc9754e659f125', 1),
(7, 'client3', '62608e08adc29a8d6dbc9754e659f125', 1),
(8, 'client4', '62608e08adc29a8d6dbc9754e659f125', 1),
(9, 'client5', '62608e08adc29a8d6dbc9754e659f125', 1),
(10, 'client6', '62608e08adc29a8d6dbc9754e659f125', 1),
(11, 'client7', '62608e08adc29a8d6dbc9754e659f125', 1),
(12, 'client8', '62608e08adc29a8d6dbc9754e659f125', 1);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `client_info`
--
ALTER TABLE `client_info`
  ADD CONSTRAINT `fk_client_info_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `tour_purchase`
--
ALTER TABLE `tour_purchase`
  ADD CONSTRAINT `FK_CLIENT` FOREIGN KEY (`client_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_TOUR` FOREIGN KEY (`tour_id`) REFERENCES `tour` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
