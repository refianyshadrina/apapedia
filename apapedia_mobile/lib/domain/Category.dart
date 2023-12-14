import '../domain/Catalog.dart';

class Category {
  final String categoryId;
  final String categoryName;
  final List<Catalog> listCatalog;

  Category({
    required this.categoryId,
    required this.categoryName,
    required this.listCatalog,
  });

  factory Category.fromJson(Map<String, dynamic> json) => Category(
        categoryId: json['categoryId'],
        categoryName: json['categoryName'],
        listCatalog: List<Catalog>.from(
            json['listCatalog'].map((item) => Catalog.fromJson(item))),
      );

  Map<String, dynamic> toJson() => {
        'categoryId': categoryId,
        'categoryName': categoryName,
        'listCatalog': List<dynamic>.from(
            listCatalog.map((catalog) => catalog.toJson())),
      };
  }