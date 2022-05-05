import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/venda">
        <Translate contentKey="global.menu.entities.venda" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/servico">
        <Translate contentKey="global.menu.entities.servico" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/item-venda">
        <Translate contentKey="global.menu.entities.itemVenda" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cliente">
        <Translate contentKey="global.menu.entities.cliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/colaborador">
        <Translate contentKey="global.menu.entities.colaborador" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pagamento">
        <Translate contentKey="global.menu.entities.pagamento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plataforma-pagamento">
        <Translate contentKey="global.menu.entities.plataformaPagamento" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/carteira-cliente">
        <Translate contentKey="global.menu.entities.carteiraCliente" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/lancamento-carteira-cliente">
        <Translate contentKey="global.menu.entities.lancamentoCarteiraCliente" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
