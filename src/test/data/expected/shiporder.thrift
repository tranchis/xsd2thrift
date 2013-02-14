namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct itemType
{
	1 : optional string note,
	2 : required double price,
	3 : required i64 quantity,
	4 : required string title,
}

struct shiptoType
{
	1 : required string address,
	2 : required string city,
	3 : required string country,
	4 : required string name,
}

struct shiporderType
{
	1 : required list<itemType> item,
	2 : required string orderid,
	3 : required string orderperson,
	4 : required shiptoType shipto,
}

