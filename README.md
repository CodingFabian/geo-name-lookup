geo-name-lookup
===============

Short snippets of code to easily map the location of an image to a geo-name, using apache tika, mongodb and a geonames.org dump

It turns out that today it is very easy to do geo lookups.
MongoDB has a strange syntax. But once you get over it it works really well and fast.
Apache Tika is a great versatile framework and can extract geolocation from many formats, but in practice it works best with pictures taken with mobile phones.
The most interesting part is selecting what data to display. geonames.org has plenty of data.
You might not use everything. Perhaps filter for cities or districts only.