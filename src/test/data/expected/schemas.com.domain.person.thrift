namespace * schemas.com.domain.person

include "schemas.com.domain.address.thrift"
include "schemas.com.domain.common.thrift"

struct personType
{
	1 : required string name,
	2 : required schemas.com.domain.common.country domicile,
	3 : required schemas.com.domain.address.addressType address,
}

