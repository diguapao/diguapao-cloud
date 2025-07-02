import pymysql

"""
当成mysqldb一样使用
MySqldb是用于Python与Mysql数据库的接口，
但是mysqldb目前只支持Python2版本，为了保证在Python3中正常链接mysql数据库，可以
"""
pymysql.install_as_MySQLdb()
