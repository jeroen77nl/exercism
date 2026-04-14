#include <string>

namespace log_line {
    std::string message(std::string line) {
        size_t pos = line.find("]:");
        return line.substr(pos + 3);
    }

    std::string log_level(std::string line) {
        size_t startPos = line.find("[");
        size_t endPos = line.find("]");
        size_t substrLength = endPos - startPos - 1;
        return line.substr(startPos + 1, substrLength);
    }

    std::string reformat(std::string line) {
        return message(line) + " (" + log_level(line) + ")";
    }
}
