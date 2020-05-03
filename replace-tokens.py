from sys import argv
from os import environ
import re

PRESUFFIX = "__"
PATTERN = PRESUFFIX + "\w+" + PRESUFFIX


class MissingEnvVarError(Exception):
    pass


def process_line(line):
    matches = re.findall(PATTERN, line)
    for match in matches:
        match_without_presuffix = re.sub(PRESUFFIX, "", match)
        value_from_environ = environ.get(match_without_presuffix)
        if not value_from_environ:
            raise MissingEnvVarError("Envvar '{}' missing".format(match_without_presuffix))
        line = re.sub(
            match,
            environ.get(match_without_presuffix, default="VALUE_NOT_FOUND"),
            line)
    return line


if __name__ == "__main__":
    files = argv[1:]
    for file_name in files:
        read_file_lines = []
        with open(file_name) as opened_f:
            read_file_lines = opened_f.readlines()
        write_file_lines = \
            [
                process_line(line)
                for line in read_file_lines
            ]
        with open(file_name, "w") as opened_f:
            opened_f.writelines(write_file_lines)
