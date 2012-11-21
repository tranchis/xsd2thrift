namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct shiptoType
{
	1 : required string name,
	2 : required string address,
	3 : required string city,
	4 : required string country,
}

struct itemType
{
	1 : required string title,
	2 : optional string note,
	3 : required i64 quantity,
	4 : required double price,
}

struct shiporderType
{
	1 : required string orderperson,
	2 : required shiptoType shipto,
	3 : required list<itemType> item,
	4 : required string orderid,
}

