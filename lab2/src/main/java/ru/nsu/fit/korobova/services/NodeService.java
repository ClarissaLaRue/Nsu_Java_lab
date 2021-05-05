package ru.nsu.fit.korobova.services;

import ru.nsu.fit.korobova.db.Database;
import ru.nsu.fit.korobova.generatedSheme.xjc.Node;
import ru.nsu.fit.korobova.models.Tag;
import org.apache.commons.collections4.CollectionUtils;
import ru.nsu.fit.korobova.services.dao.DaoNodeServices;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NodeService {

    private final DaoNodeServices _daoNodeService;

    public NodeService(Database database) {
        this._daoNodeService = new DaoNodeServices(database);
    }


    public void addNodeWithSimpleInsert(List<Node> nodes) throws SQLException {
        List<ru.nsu.fit.korobova.models.Node> parsedNodes = nodes.stream().map(NodeService::mapNode).collect(Collectors.toList());
        _daoNodeService.addNodeListBySqlRequest(parsedNodes);

    }

    public void addNodeWithPreparedStatement(List<Node> nodes) throws SQLException {
        List<ru.nsu.fit.korobova.models.Node> parsedNodes = nodes.stream().map(NodeService::mapNode).collect(Collectors.toList());
        _daoNodeService.addNodeListByPreparedStatement(parsedNodes);

    }

    public void addNodesByBatch(List<Node> nodes) throws SQLException {
        List<ru.nsu.fit.korobova.models.Node> parsedNodes = nodes.stream().map(NodeService::mapNode).collect(Collectors.toList());
        _daoNodeService.addNodeListByBatch(parsedNodes);
    }

    private static ru.nsu.fit.korobova.models.Node mapNode(Node node){
        return ru.nsu.fit.korobova.models.Node.builder()
                .id(node.getId())
                .user(node.getUser())
                .tags(mapTagList(node.getTag(), node.getId()))
                .build();
    }

    private static List<Tag> mapTagList(List<ru.nsu.fit.korobova.generatedSheme.xjc.Tag> tags, BigInteger nodeId) {
        if (CollectionUtils.isEmpty(tags)) {
            return Collections.emptyList();
        }
        return tags.stream()
                .map(tag -> new Tag(tag.getK(), tag.getV(), nodeId))
                .collect(Collectors.toList());
    }
}
