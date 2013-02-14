namespace * default

struct UnspecifiedType
{
	1 : required string baseObjectType,
	2 : required binary object,
}

struct ingredientsType
{
	1 : required list<string> item,
}

struct bulletType
{
	1 : optional list<string> strong,
}

struct paraType
{
	1 : optional list<string> strong,
}

struct metaType
{
	1 : required string author,
	2 : required string _date,
	3 : required string version,
}

struct directionsType
{
	1 : optional paraType para,
	2 : optional bulletType bullet,
}

struct recipeType
{
	1 : required metaType meta,
	2 : optional string recipe_author,
	3 : required string recipe_name,
	4 : required string meal,
	5 : required ingredientsType ingredients,
	6 : required directionsType directions,
}

struct listType
{
	1 : required list<recipeType> recipe,
}

